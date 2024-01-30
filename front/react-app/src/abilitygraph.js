import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Radar } from 'react-chartjs-2';
import { Chart as ChartJS, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend } from 'chart.js';

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

const RadarChartExample = ({jsondata}) => {


  
  
  let chartData = {
    labels: ['통솔력', '무력', '지력', '정치력', '매력'],
    datasets: [
      {
        label: '',
        backgroundColor: 'rgba(0, 0, 0, 0.6)',
        borderColor: 'rgba(0, 0, 0, 1)',
        pointBackgroundColor: 'rgba(0, 0, 0, 1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(0, 0, 0, 0.6)',
        data: [],
      },
    ],
  };
  
        // 가져온 JSON 데이터 중에서 필요한 값만 추출하여 데이터셋에 적용
        if (jsondata && jsondata.ancestor) {
          const extractedData = {
            tong: jsondata.ancestor.tong,
            mu: jsondata.ancestor.mu,
            ji: jsondata.ancestor.ji,
            jung: jsondata.ancestor.jung,
            mae: jsondata.ancestor.mae,
          };

        // 차트 데이터 갱신
        const newData = {
          ...chartData,
          datasets: [
            {
              ...chartData.datasets[0],
              label: jsondata.ancestor.name,
              data: Object.values(extractedData),
            },
          ],
        };


        chartData = newData;
        }


        else {
       return <div style={{ textAlign: 'center', fontWeight: 'bold', fontSize: '24px' }}>데이터가 없습니다.</div>;;
        }


        return (
          <div style={{ width: '700px', height: '670px',margin: 'auto',}}>
            
            <Radar data={chartData} options={chartOptions} />
          </div>
        );

      }
     
 // 빈 배열을 넣어 한 번만 실행되도록 설정 (마운트 시에만 실행)

  // 차트 스타일 설정
  const chartOptions = {

    scales: {
        r: {
          min: 10, // 최소값
          max: 100, // 최대값
          stepSize: 20,
         // 간격
        },
        
        
      },
      


      
    maintainAspectRatio: true,  // 차트가 부모 컨테이너에 맞게 크기를 유지하지 않도록 설정
    responsive: true, // 반응형으로 크기를 조정할 수 있도록 설정
    width: 400, // 원하는 가로 크기
    height: 400, // 원하는 세로 크기
  };

  

export default RadarChartExample;
