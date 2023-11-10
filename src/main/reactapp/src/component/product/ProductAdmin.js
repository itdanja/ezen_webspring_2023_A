/*  1. mui Tabs[탭 메뉴] import  */
import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext'; // npm i @mui/lab 설치 필수
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

import CategoryWrite from './CategoryWrite'
import ProductInfo from './ProductInfo'
import ProductList from './ProductList'
import ProductWrite from './ProductWrite'

export default function ProductAdmin( props ){
    // 2.
    const [value, setValue] = React.useState('1');
    // 3.
    const handleChange = (event, newValue) => { setValue(newValue); };
    return(<>
         {/*4.*/}
         <Box sx={{ width: '100%', typography: 'body1' }}>
           <TabContext value={value}>
             <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
               {/* 탭 에 포함된 제목들 */}
               <TabList
                    onChange={handleChange}
                    aria-label="lab API tabs example"
                    indicatorColor="secondary"
                    centered
                    >
                 <Tab label="카테고리 등록" value="1" /> { /* value : 탭순서번호(식별) */}
                 <Tab label="제품 등록" value="2" />
                 <Tab label="제품 목록" value="3" />
                 <Tab label="제품 상태" value="4" />
               </TabList>
             </Box>
              {/* 탭 선택시 출력되는 내용물 */}
             <TabPanel value="1"> <CategoryWrite /> </TabPanel>
             <TabPanel value="2"> <ProductWrite /> </TabPanel>
             <TabPanel value="3"> <ProductList /> </TabPanel>
             <TabPanel value="4"> <ProductInfo /> </TabPanel>
           </TabContext>
         </Box>
    </>)
}