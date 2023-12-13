export interface Solution {
    id: number;
    title: string;
    provider: string;
    number: number;
    username: string;
}

export interface SolutionList {
    items: Solution[];
}
