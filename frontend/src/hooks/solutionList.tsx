import { useEffect, useState } from "react";

import { solutionList } from "../apis/v1/solutions/list";
import { Solution } from "../types/solution/Solution";

export function useSolutionList() {
  const [solution_list, setSolutionList] = useState<Solution[] | undefined>([]);

  useEffect(() => {
    const initSolutionList = async () => {
      const data = await solutionList();
      console.log(data);
      setSolutionList(data);
    };
    initSolutionList();
  }, []);

  return solution_list;
}
