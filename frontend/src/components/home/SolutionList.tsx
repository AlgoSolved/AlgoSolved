import React, { useEffect, useState } from "react";

import { Container, List, ListItem, ListItemText } from "@mui/material";
import { solutionList } from "../../apis/v1/solutions";
import { Solution } from "../../types/solution/Solution";

const CurrentList = () => {
  const [solution_list, setSolutionList] = useState<Solution[] | null>(null);

  useEffect(() => {
    const initSolutionList = async () => {
      const list = await solutionList();
      setSolutionList(list || null);
    };
    initSolutionList();
  }, []);

  return (
    <div>
      <Container>
        <div style={{ width: "fit-content", textDecoration: "bold" }}>
          최근 문제 풀이
        </div>
        {solution_list === null ? (
          <div style={{ textAlign: "left" }}>최근 문제 풀이가 없습니다.</div>
        ) : (
          <List sx={{ width: "100%", bgcolor: "background.paper" }}>
            {solution_list?.map((solution) => (
              <ListItem
                key={solution.id}
                disableGutters
                secondaryAction={solution.username}
              >
                <ListItemText
                  primary={
                    solution.number +
                    " " +
                    solution.provider +
                    " - " +
                    solution.title
                  }
                />
              </ListItem>
            ))}
          </List>
        )}
      </Container>
    </div>
  );
};

export default CurrentList;
