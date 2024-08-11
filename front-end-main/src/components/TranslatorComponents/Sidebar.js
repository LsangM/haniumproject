import React, { useState } from 'react';
import '../styles/Sidebar.css';
import { Button, Divider, IconButton, List, ListItem, ListItemButton, ListItemText, ThemeProvider, createTheme } from '@mui/material';
import DeleteOutlinedIcon from '@mui/icons-material/DeleteOutlined';
import AddIcon from '@mui/icons-material/Add';
import { useSelector, useDispatch } from "react-redux";
import axios from "axios";

import mockWorkListAdd from '../../assets/mockdata/mockWorkListAdd';

const Sidebar = ({ selectedWork, setSelectedWork, sidebarOpen, toggleSidebar, workList, setWorkList, setResults, setWorkId, setTitle }) => {

    const token = useSelector((state) => state.token.token);

    const theme = createTheme({
        palette: {
            mode: 'dark',
            primary: {
                main: "rgb(255,255,255)",
            },
        }
    });

    // 작업 추가
    const handleAddButton = () => {
        // axios.post("http://localhost:8080/worklist", {
        //     title: "새 번역",
        //     type: "translation",
        // }, { 
        //     headers: { 'Authorization': `Bearer ${token}` }
        // }).then(res => {
        //     setWorkList(res.data.data);
        // }).catch(error => {
        //     console.log(error);
        // });
        setWorkList(mockWorkListAdd);
    };

    // 작업 선택
    const handleListItemClick = (index, work) => {
        setSelectedWork(index);
        setWorkId(work.workId);
        fetchResults(work.workId);
        setTitle(work.title);
    };

    const fetchResults = (workId) => {
        axios.get("http://localhost:8080/results/" + workId, { 
            headers: { 'Authorization': `Bearer ${token}` }
        }).then(res => {
            console.log(res.data.data);
            setResults(res.data.data);
        }).catch(error => {
            console.log(error);
        });
    }


    // 작업 삭제
    const handleDeleteButtonClick = (workId) => {
        deleteWork(workId);
    };

    const deleteWork = (workId) => {
        axios.put("http://localhost:8080/worklist/delete", {
            workId: workId,
        }, { 
            headers: { 'Authorization': `Bearer ${token}` }
        }).then(res => {
            setWorkList(res.data.data);
        }).catch(error => {
            console.log(error);
        });
    };

    return (
        <div className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
            <ThemeProvider theme={theme}>
                <div className='sidebarTop'>
                    <Button className="addButton" variant="outlined" sx={{ justifyContent: "flex-start" }} onClick={handleAddButton}>
                        <AddIcon sx={{ color: 'rgba(255,255,255,0.9)', marginRight: '5px' }} />
                        <span>새 번역</span>
                    </Button>
                    <div className="sidebarButton" style={{ opacity: sidebarOpen ? "" : "0" }} onClick={toggleSidebar}>
                        ✕
                    </div>
                </div>
                <div className='workList'>
                    <List>
                        <Divider sx={{ bgcolor: "rgba(255, 255, 255, 0.2)" }} />
                        {workList.map((work, index) => (
                            <ListItem key={work.workId} disablePadding selected={selectedWork === index}
                                secondaryAction={
                                    <>
                                        {selectedWork === index &&
                                            <IconButton edge="end" aria-label="delete">
                                                <DeleteOutlinedIcon onClick={() => handleDeleteButtonClick(work.workId)} sx={{ color: "rgba(255,255,255,0.3)", '&:hover': { color: "rgba(255,255,255,0.9)" } }} />
                                            </IconButton>
                                        }
                                    </>
                                }>
                                <ListItemButton onClick={() => handleListItemClick(index, work)}>
                                    <ListItemText primary={work.title} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                </div>
            </ThemeProvider>
        </div>
    )
}

export default Sidebar;
