export interface BaseResponse<T> {
  code: string;
  data?: T;
  message: string;
  error?: {
    code: string;
    message: string;
    description: string;
  };
}

export interface ApiResponseProps<T> {
  status: number;
  data: BaseResponse<T>;
}
