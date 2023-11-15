// 터미널[alt+f12] -> cd src/main/reactapp -> npm install @mui/x-charts
import { BarChart } from '@mui/x-charts/BarChart'; // 막대차트
import { PieChart } from '@mui/x-charts/PieChart'; // 원형차트
import axios from 'axios';
import { useState , useEffect } from 'react'; // react hook
export default function ProductInfo( props ){

    // 1. 컴포넌트 실행시 막대차트에 필요한 데이터 요청 axios
    const [ barChartData , setBarChartData ] = useState([]);
    const getBarChart = (e) =>{
        axios.get("/product/barchart")
            .then( r =>{
                setBarChartData(r.data);
            } )
    }
    useEffect( ()=>{ getBarChart() } , [] )

    // 2. 컴포넌트 실행시 원형차트에 필요한 데이터 요청 axios
    const [ pieChartData , setPieChartData ] = useState([]);
    const getPieChart = (e) =>{
        axios.get("/product/piechart")
            .then( r =>{ console.log(r.data); setPieChartData( r.data);  } )
    }
    useEffect( ()=>{ getPieChart() } , [] )

    return(<>
        <div style={{ display : 'flex' }}>
            <div>
                <h3> 제품별 재고 수 (막대차트) </h3>
                { /* barChartData에 데이터 개수가 0 이 아니면 차트 표시 O  /   0 이면 차트 표시 X  */}
                {
                    barChartData.length != 0 ?
                       <BarChart
                          xAxis={[
                            { id: 'barCategories',
                             data: barChartData.map( (p)=>{ return p.pname }),
                             scaleType: 'band',  },
                          ]}
                          series={[
                            {
                                data: barChartData.map( (p)=>{ return p.pstock }),
                             },
                         ]}
                          width={500} height={300}
                        />
                        : <></>
                }
            </div>
            <div>
                <h3> 카테고리별 제품 수 (원형차트) </h3>
                 <PieChart
                      series={[
                        {
                          data: pieChartData.map( (p,i)=>{ return { id: i , value : p.count , label : p.pcname  }  }),
                        },
                      ]}
                      width={400}
                      height={200}
                    />
            </div>
        </div>
    </>)
}
















