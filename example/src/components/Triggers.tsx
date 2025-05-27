import {
  Button,
  PermissionsAndroid,
  ScrollView,
  StyleSheet,
  View,
} from 'react-native';
import { Box } from './index';
import NativeModels from 'rn-toolkit';
import ToolkitTriggerManager, {
  type IToolkitTrigger,
  type IToolkitTriggerResult,
} from '../ToolkitTriggerManager';
import { useState } from 'react';
import { generateItems } from '../utils/generateItems';
import List from './List';
import Title from './Title';

const Triggers = ({ back }: { back: () => void }) => {
  const [data, setData] = useState<Array<IToolkitTriggerResult>>([]);

  const requestPermissions = async () => {
    return new Promise(async (resolve) => {
      await PermissionsAndroid.requestMultiple([
        PermissionsAndroid.PERMISSIONS.READ_CONTACTS,
        PermissionsAndroid.PERMISSIONS.READ_CALL_LOG,
        PermissionsAndroid.PERMISSIONS.READ_SMS,
        PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
      ]);
      resolve(null);
    });
  };

  const firstLaunchTrigger = () => {
    const firstLaunchParams: IToolkitTrigger = {
      install_id: NativeModels.getInstallId,
      install_referrer: NativeModels.getInstallReferrer,
    };

    setData([]);
    ToolkitTriggerManager.runTrigger(firstLaunchParams).then(setData);
  };

  const getCoarseLocation = async () => {
    await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION
    );
    return NativeModels.getCoarseLocation();
  };

  const newSessionTrigger = async () => {
    const newSessionParams: IToolkitTrigger = {
      app_version: NativeModels.getAppVersion,
      os_parameters: NativeModels.getOSParameters,
      session_id: NativeModels.createSessionId,
      date_time: NativeModels.getLocalDateTime,
      location: getCoarseLocation,
      sms: NativeModels.getSMS,
      contacts: NativeModels.getContacts,
      calls: NativeModels.getCalls,
      fingerprint: NativeModels.getFingerprint,
    };

    setData([]);

    await requestPermissions();
    ToolkitTriggerManager.runTrigger(newSessionParams)
      .then((res) => {
        console.log(res);
        setData(res);
      })
      .catch((er) => {
        console.log(er);
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
