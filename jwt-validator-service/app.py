from flask import Flask, request
import jwt
import requests
import logging

app = Flask(__name__)

# Check in http://traefik.auth.localdev.me/realms/myrealm/.well-known/openid-configuration
KEYCLOAK_URL = "http://keycloak-svc/realms/myrealm/protocol/openid-connect/certs"
#KEYCLOAK_URL = "http://host.docker.internal/realms/myrealm/protocol/openid-connect/certs"
PUBLIC_KEYS = {}

def get_public_key(kid):
    """Fetch and cache the public key from Keycloak's JWKS endpoint."""
    if kid in PUBLIC_KEYS:
        return PUBLIC_KEYS[kid]

    jwks_response = requests.get(KEYCLOAK_URL)
    jwks = jwks_response.json()

    for key in jwks["keys"]:
        if key["kid"] == kid:
            # Construct the public key
            public_key = jwt.algorithms.RSAAlgorithm.from_jwk(key)
            PUBLIC_KEYS[kid] = public_key
            return public_key

    return None

@app.route("/validate", methods=["POST","GET","PUT"])
def validate_jwt():
    """Validate the JWT token from the Authorization header."""
    auth_header = request.headers.get("Authorization")
    if not auth_header:
        return "Authorization header is missing", 401
    
    token = auth_header.split(" ")[1]
    try:
        # Decode the token header without verification to fetch the `kid`
        unverified_header = jwt.get_unverified_header(token)
        kid = unverified_header["kid"]
        
        public_key = get_public_key(kid)
        if not public_key:
            return "Public key not found for kid", 401

        # Verify the token
        jwt.decode(token, public_key, algorithms=["RS256"], audience="account")
        
        # Token is valid
        return "Authorized", 200
    except jwt.PyJWTError as e:
        logger.error(e)
        # Token is invalid
        return str(e), 401

if __name__ == "__main__":
    logger = logging.getLogger('waitress')
    logger.setLevel(logging.DEBUG)

    from waitress import serve
    serve(app, port=5000)
