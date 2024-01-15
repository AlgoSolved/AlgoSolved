import { solutionList } from "../apis/v1/solutions";
import { Solution } from "@/types/solution/Solution";
import { useEffect, useState } from "react";

export default function useSolutionList() {
  const [solution_list, setSolutionList] = useState<Solution[] | null>(null);

  useEffect(() => {
    const initSolutionList = async () => {
      const list = await solutionList();
      setSolutionList(list || null);
    };
    initSolutionList();
  }, []);

  return solution_list;
}
