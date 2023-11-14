import { BarChart } from '@mui/x-charts/BarChart';
import { PieChart } from '@mui/x-charts/PieChart';

import { useState , useEffect  } from 'react'
import axios from 'axios'

export default function ProductInfo( props ){

    const [ barChart , setBarChart ] = useState([]);
    const barChartData = (e)=>{
        axios.get( '/product/barChartData' ).then( r=>{ console.log(r.data ); setBarChart(r.data) } )
    }
    useEffect( ()=>{ barChartData() } , [])

    const [ pieChart , setPieChart ] = useState([]);
    const pieChartData = (e)=>{
        axios.get( '/product/pieChartData' ).then( r=>{ console.log(r.data ); setPieChart(r.data) } )
    }
    useEffect( ()=>{ pieChartData() } , [])

    return(<>
        <div style={{ display : 'flex' }}>
            <div>
                <h3> 제품별 재고 상태 </h3>
                {
                    barChart.length != 0 ?
                        <BarChart
                          xAxis={[
                            {
                              id: 'barCategories',
                              data: barChart.map( (p)=>{ return  p.pname } ) ,
                              scaleType: 'band',
                            },
                          ]}
                          series={[
                            {
                              data: barChart.map( (p)=>{ return  p.pstock } ) ,
                              label: '재고량',
                            },
                          ]}
                          width={500}
                          height={300}
                        />
                    :
                    <></>
                }
            </div>
            <div>
                <h3> 카테고리별 제품수 </h3>
                <PieChart
                  series={[
                    {
                      data :  pieChart.map( (p,index)=>{ return { id: index, value: p.count, label: p.pcname } })
                    },
                  ]}
                  width={500}
                  height={300}
                />
            </div>
        </div>
    </>)
}