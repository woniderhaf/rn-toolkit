import { Button, ScrollView, StyleSheet, View } from 'react-native';
import { Box } from './index';
import NativeModels, {
  type IdParamsType,
  type IResult,
  NativeModulesTriggers,
} from 'rn-toolkit';
import { useState } from 'react';
import { generateItems } from '../utils/generateItems';
import List from './List';
import Title from './Title';

const Triggers = ({ back }: { back: () => void }) => {
  const [data, setData] = useState<Array<IResult>>([]);

  const firstLaunchTrigger = () => {
    const firstLaunchParams: IdParamsType = {
      install_id: NativeModels.getInstallId,
      install_referrer: NativeModels.getInstallReferrer,
    };

    setData([]);
    NativeModulesTriggers.firstLaunchTrigger(firstLaunchParams).then((res) => {
      setData(res);
    });
  };

  const newSessionTrigger = () => {
    const newSessionParams: IdParamsType = {
      app_version: NativeModels.getAppVersion,
      os_parameters: NativeModels.getOSParameters,
      session_id: NativeModels.createSessionId,
      date_time: NativeModels.getLocalDateTime,
      location: NativeModels.getCoarseLocation,
      fingerprint: NativeModels.getFingerprint,
    };

    setData([]);
    NativeModulesTriggers.newSessionTrigger(newSessionParams).then((res) => {
      setData(res);
    });
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.wrap}>
        <Button title={'back'} onPress={back} />
        <Box title={'Данные которые улетят по триггеру'}>
          {data.map((item) => {
            if (typeof item.value === 'object') {
              return (
                <View key={item.id} style={styles.object}>
                  <Title key={item.id + new Date().getTime()} title={item.id} />
                  {generateItems(item.value)}
                </View>
              );
            }
            return (
              <List
                key={item.id}
                title={item.id}
                value={JSON.stringify(item.value)}
              />
            );
          })}
        </Box>
        <Box title={"инициируем событие 'first launch'"}>
          <Button title={'first launch'} onPress={firstLaunchTrigger} />
        </Box>
        <Box title={"инициируем событие 'new session'"}>
          <Button title={'new session'} onPress={newSessionTrigger} />
        </Box>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.6)',
  },
  wrap: {
    gap: 10,
    paddingVertical: 40,
  },
  object: {
    backgroundColor: 'rgba(0,0,0,0.2)',
    padding: 5,
  },
});

export default Triggers;
