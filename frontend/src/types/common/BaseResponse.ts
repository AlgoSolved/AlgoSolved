export interface BaseResponse<T> {
  code: string;
  data: T;
  error?: {
    code: string;
    message: string;
    description: string;
  };
}

export interface ApiResponseProps<T> {
  status: number;
  body: BaseResponse<T>;
}
