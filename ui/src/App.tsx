import React, {useEffect, useState} from 'react';
import 'antd/dist/antd.css';
import { Typography, Divider } from 'antd';
import { Card, Progress } from 'antd';


import API from './service/API';


function App() {
    const [challenge, setChallenge] = useState<any>({})

    useEffect(() => {
        API.get('/api/v1/challenges/ca3d68d4-dbb1-4c63-87e3-a2d22f2ac16e')
            .then((res: any) => {
                setChallenge(res.data)
            });
    }, []);



    console.log(challenge)
    const { Title } = Typography;
  return (
    <div className="App">
                <Title level={3}>text challenge</Title>
                <Divider />
        <Card hoverable title="title challenge"  style={{ width: 300, margin: "0 auto",}}>
            <p>goal challenge: 300</p>
            <Progress
                type="circle"
                strokeColor={{
                    '0%': '#108ee9',
                    '100%': '#87d068',
                }}
                percent={75}
            />
        </Card>

        <h2>
            {challenge.name}
        </h2>
        <h2>
            {challenge.progress}
        </h2>
        <ul>
            {challenge.users && challenge.users.map((el: any, idx: number) =>
                <li key={idx}>{ el.userId }</li>
            )}
        </ul>
        <ul>
            {challenge.submissions && challenge.submissions.map((el: any, idx: number) =>
                <li key={idx}>{ el.userId } {el.value}</li>
            )}
        </ul>
    </div>
  );
}

export default App;
