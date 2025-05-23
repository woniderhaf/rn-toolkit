import { useState } from 'react';
import { AllData, Triggers } from './components';
import { Button, View } from 'react-native';

export default function App() {
  const [type, setType] = useState<number>(0);

  const render = () => {
    switch (type) {
      case 1:
        return <Triggers back={() => setType(0)} />;
      case 2:
        return <AllData back={() => setType(0)} />;
      default:
        return (
          <View
            style={{
              flex: 1,
              justifyContent: 'center',
              gap: 20,
              backgroundColor: 'rgba(0,0,0,0.6)',
            }}
          >
            <Button title={'triggers'} onPress={() => setType(1)} />
            <Button title={'all data'} onPress={() => setType(2)} />
          </View>
        );
    }
  };

  return <View style={{ flex: 1 }}>{render()}</View>;
}
