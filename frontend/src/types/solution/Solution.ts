export interface Solution {
    id: number;
    title: string;
    provider: string;
    number: number;
    author: string;
}

export interface SolutionList {
    items: Solution[];
}