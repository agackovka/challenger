import React, {useEffect, useState} from 'react';
import API from './service/API';


function App() {
    const [challenge, setChallenge] = useState<any>({})

    useEffect(() => {
        API.get('/api/v1/challenges/1b87bdd5-df75-47be-a8f6-a8b3b4a935d5')
            .then((res: any) => {
                console.log(res.data);
                setChallenge(res.data)
            });
    }, []);



  return (
    <div className="App">
      <h1>HI</h1>
      <h2>
          {challenge.name}
      </h2>
    </div>
  );
}

export default App;
