import { Solution } from '@/types/solution/Solution';
import { ApiClient } from '../ApiClient';


export const solutionList = async () => {
    try {
        const { status, data } = await ApiClient.get<Solution[]>(`api/v1/solution/list`);
        return data;
    } catch (error: any) {
        // TODO: 에러 처리 의논
        console.log(error.response.code);
    }
};
