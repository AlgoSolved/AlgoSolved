import { useEffect, useState } from "react";

import { getSolutionDetail } from "../apis/v1/solutions/detail";
import { SolutionDetail } from "../types/solution/Solution";

export function useSolutionDetail(id: any) {
  const [solutionDetail, setSolutionDetail] = useState<SolutionDetail>();

  useEffect(() => {
      const initSolutionDetail = async () => {

      const data = await getSolutionDetail(id);

      setSolutionDetail(data);
    };
    initSolutionDetail();
  }, []);

  return solutionDetail;
}
