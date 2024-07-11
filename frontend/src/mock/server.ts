import { setupServer } from "msw/node";

import getBlankSolutionList from "./api/solution_list";

export const server = setupServer(...getBlankSolutionList);
