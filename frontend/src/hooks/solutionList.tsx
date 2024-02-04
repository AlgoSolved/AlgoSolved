import { solutionList } from "../apis/v1/solutions";
import { Solution } from "../types/solution/Solution";
import { useEffect, useState } from "react";

export function useSolutionList() {
  const [solution_list, setSolutionList] = useState<Solution[] | undefined>([]);

  useEffect(() => {
    const initSolutionList = async () => {
      const data = await solutionList();
      setSolutionList(data);
    };
    initSolutionList();
  }, []);

  return solution_list;
}
