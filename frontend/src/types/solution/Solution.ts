export interface Solution {
  problemName: string;
  problemType: string;
  problemNumber: number;
  username: string;
}

export interface SolutionList {
  items: Solution[];
}
