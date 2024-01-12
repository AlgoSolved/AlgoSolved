export class AxiosErrorClass extends Error {
  response?: {
    data: any;
    status: number;
    headers: string;
  };
}
