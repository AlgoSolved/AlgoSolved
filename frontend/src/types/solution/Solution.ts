export interface Solution {
  id: number;
  problemName: string;
  problemType: string;
  problemNumber: string;
  userName: string;
}

export interface SolutionList {
  items: Solution[];
}

export interface SolutionDetail {
  language: string;
  sourceCode: string;
  problemName: string;
  problemNumber:1000;
  link: string;
  rank: string;
}
