import { http, HttpResponse } from "msw";

const handlers = [
  http.get("api/v1/solutions/recent-list", () => {
    return HttpResponse.json([
      {}
    ]);
  }),
];

export default handlers;
