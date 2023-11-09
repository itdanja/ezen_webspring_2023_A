import { useSearchParams , Link } from 'react-router-dom'
import axios from 'axios'
import { useState , useEffect } from 'react'

import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext'; // npm i @mui/lab
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

import CategoryWrite  from './CategoryWrite'
import ProductWrite from './ProductWrite'
import ProductList from './ProductList'


export default function ProductInfo( props ){

      const [value, setValue] = useState('1');

      const handleChange = (event, newValue) => {
        setValue(newValue);
      };

    return(<>
        <Box sx={{ width: '100%', typography: 'body1' }}>
          <TabContext value={value}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
              <TabList onChange={handleChange} aria-label="lab API tabs example" centered>
                <Tab label="제품 카테고리" value="1" />
                <Tab label="제품 등록" value="2" />
                <Tab label="제품 현황" value="3" />
              </TabList>
            </Box>
            <TabPanel value="1"><CategoryWrite/></TabPanel>
            <TabPanel value="2"><ProductWrite/></TabPanel>
            <TabPanel value="3"><ProductList/></TabPanel>
          </TabContext>
        </Box>
    </>)
}

