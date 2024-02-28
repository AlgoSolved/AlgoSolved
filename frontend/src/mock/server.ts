import { setupServer } from "msw/node";
import getBlankSolutionList from "./api/solution_list";
import { getBlankUserInfo, getNotBlankUserInfo } from "./api/user_info";

export const server = setupServer(...getBlankSolutionList);
