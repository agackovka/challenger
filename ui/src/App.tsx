import React, {useEffect, useState} from 'react';
import API from './service/API';


function App() {
    const [challenge, setChallenge] = useState<any>({})

    useEffect(() => {
        API.get('/api/v1/challenges/1b87bdd5-df75-47be-a8f6-a8b3b4a935d5')
            .then((res: any) => {
                setChallenge(res.data)
            });
    }, []);



    console.log(challenge)
  return (
    <div className="App">
      <h1>HI</h1>

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
