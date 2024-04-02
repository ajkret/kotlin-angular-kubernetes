# Simple Traefik Forward Auth JWT Token Validation
Here an implementation of JWT validation service that can be used with Traefik's ForwardAuth middleware. This service will run in Kubernetes, which will
extract the JWT from the Authorization header of incoming requests using a Traefik Middleware, forward to validating token service, and respond to Traefik with an HTTP status code indicating whether the request is authorized.

## Middleware Manifest

    apiVersion: traefik.containo.us/v1alpha1
    kind: Middleware
    #metadata:
      name: jwt-validator
    spec:
      forwardAuth:
        address: http://jwt-validator.localdev.me/validate
        trustForwardHeader: true
        authResponseHeaders:
          - X-User

## Dependencies

This small application was implemented in Python and obtained using ChatGPT. It depends on 



