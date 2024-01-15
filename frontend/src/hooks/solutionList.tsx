import { solutionList } from "../apis/v1/solutions";
import { Solution } from "@/types/solution/Solution";
import { useEffect, useState } from "react";

export default function useSolutionList() {
  const [solution_list, setSolutionList] = useState<Solution[] | undefined>([]);

  useEffect(() => {
    const initSolutionList = async () => {
      const list = await solutionList();
      setSolutionList(list?.body?.data);
    };
    initSolutionList();
  }, []);

  return solution_list;
}
