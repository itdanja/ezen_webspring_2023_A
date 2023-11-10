/*  1. mui Tabs[탭 메뉴] import  */
import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext'; // npm i @mui/lab 설치 필수
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

export default function ProductAdmin( props ){
    // 2.
    const [value, setValue] = React.useState('1');
    // 3.
    const handleChange = (event, newValue) => { setValue(newValue); };

    return(<>
         <h3> 제품 관리 페이지 </h3>
         {/*4.*/}
         <Box sx={{ width: '100%', typography: 'body1' }}>
           <TabContext value={value}>
             <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
               <TabList onChange={handleChange} aria-label="lab API tabs example">
                 <Tab label="Item One" value="1" />
                 <Tab label="Item Two" value="2" />
                 <Tab label="Item Three" value="3" />
               </TabList>
             </Box>
             <TabPanel value="1">Item One</TabPanel>
             <TabPanel value="2">Item Two</TabPanel>
             <TabPanel value="3">Item Three</TabPanel>
           </TabContext>
         </Box>
    </>)
}