export class Task {
  constructor(
    public task: string,
    public dueTo: Date,
    public completed: boolean,
    public id?: number) { }
}
