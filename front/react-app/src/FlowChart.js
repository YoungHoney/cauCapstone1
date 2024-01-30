import React  from 'react';


function FlowChart({ jsondata }) {

  if (!jsondata || !jsondata.govSequence) {
      return <div>데이터가 없습니다.</div>;
    }


const containerStyle = {
  display: 'flex',
  justifyContent: 'center',
  margin: '0',
};

const groupContainerStyle = {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  margin: '0px',
};

const circleStyle = {
  width: '170px',
  height: '170px',
  borderRadius: '50%',
  backgroundColor: 'white',
  border: '1px solid #000',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  margin: '20px',
  fontSize: '17px',
  fontWeight: 'bold',
};

const rectStyle = {
  width: 'auto',
  height: '60px',
  backgroundColor: 'white',
  border: '1px solid #000',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  fontSize: '17px',
  margin: '20px',
  paddingLeft: '10px',
  paddingRight: '10px',
};

const arrowContainerStyle = {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
};

// govSequence 객체를 배열로 변환

const govSequenceArray = jsondata.govSequence ? Object.entries(jsondata.govSequence) : [];

const generateFlowChart = () => {
if (!govSequenceArray || govSequenceArray.length === 0) {
return <div>데이터가 없습니다.</div>;
}

return govSequenceArray.map((sequence, index, array) => {
const [firstPart, secondPart] = sequence[1].split(',');

return (
<React.Fragment key={index}>
  <div style={groupContainerStyle}>
    <div style={circleStyle}>
    <span>{firstPart ? firstPart : '정보없음'}</span>
    </div>
    <div style={rectStyle}>
    <span>{secondPart ? secondPart : '정보없음'}</span>
    </div>
  </div>
  {index < array.length - 1 && (
    <div style={arrowContainerStyle}>
      <svg width="100" height="100">
        <line x1="0" y1="90" x2="100" y2="90" stroke="black" />
        <polygon points="95,85 100,90 95,95" fill="black" />
      </svg>
    </div>
  )}
</React.Fragment>
);
});
};

return (
  <div style={containerStyle}>
    {generateFlowChart()}
  </div>
);
}

export default FlowChart


