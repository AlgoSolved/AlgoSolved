import React, { useEffect, useState } from 'react';

import { Container, List, ListItem, ListItemText } from '@mui/material';
import { solutionList } from '../../apis/v1/solutions';
import { Solution } from '../../types/solution/Solution';
import { alignProperty } from '@mui/material/styles/cssUtils';

const dummyDataList : Solution[] = [{
    id: 0,
    title: 'dummy',
    provider: 'BOJ',
    number: 101,
    author: 'test1'
},{
    id: 1,
    title: 'dummy2',
    provider: 'BOJ',
    number: 102,
    author: 'test1'
}];


const CurrentList = () => {
    const [solution_list, setSolutionList] = useState<Solution[]>(dummyDataList);

    // useEffect(() => {
    //     const initSolutionList = async () => {
    //         const list = await solutionList();
    //         setSolutionList(list);
    //     };
    //     initSolutionList();
    // }, []);


    return (
        <div>
            <Container>
            <div style={{width: 'fit-content', textDecoration: 'bold'}}>최근 항목</div>
            <List sx={{ width: '100%', bgcolor: 'background.paper' }}>
                {solution_list?.map((solution) => (
                    <ListItem
                    key={solution.id}
                    disableGutters
                    secondaryAction={
                        solution.author
                      }
                    >
                    <ListItemText primary={solution.number +' ' + solution.provider +' - '+ solution.title} />
                    </ListItem>
                ))}
            </List>
            </Container>
        </div>
    );
}

export default CurrentList;
