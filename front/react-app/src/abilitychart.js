import React from 'react';
import { Bar } from 'react-chartjs-2';

const data = {
  labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
  datasets: [
    {
      label: 'Example Data',
      data: [12, 19, 3, 5, 2, 3],
      backgroundColor: ['red', 'blue', 'yellow', 'green', 'purple', 'orange'],
    },
  ],
};

const MyBarChart = () => {
  return (
    <div>
      <h2>Bar Chart Example</h2>
      <Bar data={data} />
    </div>
  );
};

export default MyBarChart;
