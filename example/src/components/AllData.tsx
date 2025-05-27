import {
  Button,
  PermissionsAndroid,
  ScrollView,
  StyleSheet,
  View,
} from 'react-native';
import { Box, List, Title } from './index';
import { generateItems } from '../utils/generateItems';
import Toolkit from 'rn-toolkit';
import { useEffect, useState } from 'react';
import type { ICall, IContact, ISMS } from '../../../src/interfaces';

const AllData = ({ back }: { back: () => void }) => {
  const [calls, setCalls] = useState<ICall[]>([]);
  const [sms, setSMS] = useState<ISMS[]>([]);
  const [contacts, setContacts] = useState<IContact[]>([]);
  const [fingerprint, setFingerprint] = useState<Record<string, any>>({});
  const [OSParameters, setOSParameters] = useState<Record<string, any>>({});
  const [installReferrer, setInstallReferrer] = useState<Record<string, any>>(
    {}
  );

  const [location, setLocation] = useState<Record<string, any> | null>(null);
  const [reinstall, setReinstall] = useState<string | null>(null);

  const init = async () => {
    const isFirstLaucnh = Toolkit.getIsFirstLaunch();

    const locationPermission = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION
    );

    if (locationPermission === PermissionsAndroid.RESULTS.GRANTED) {
      Toolkit.getCoarseLocation()
        .then((res) => {
          console.log(res);
          setLocation(res);
        })
        .catch((er) => {
          console.log(er);
        });
    }

    Toolkit.getOSParameters().then(setOSParameters);

    if (isFirstLaucnh) {
      Toolkit.getInstallReferrer()
        .then((res) => {
          console.log('referrer', res);
          res && setInstallReferrer(res);
        })
        .catch((er) => {
          console.log(er);
        });

      setReinstall(Toolkit.getInstallId());
    }

    await Toolkit.getFingerprint()
      .then((res) => {
        console.log(res);
        setFingerprint(res);
      })
      .catch((er) => {
        console.log('er finger: ', er);
      });

    const callsPermission = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.READ_CALL_LOG
    );
    if (callsPermission === PermissionsAndroid.RESULTS.GRANTED) {
      Toolkit.getCalls()
        .then(setCalls)
        .catch((er) => {
          console.log(er);
        });
    }

    const contactsPermission = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.READ_CONTACTS
    );

    if (contactsPermission === PermissionsAndroid.RESULTS.GRANTED) {
      Toolkit.getContacts()
        .then(setContacts)
        .catch(() => {});
    }

    const smsPermission = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.READ_SMS
    );

    if (smsPermission === PermissionsAndroid.RESULTS.GRANTED) {
      Toolkit.getSMS()
        .then(setSMS)
        .catch((er) => {
          console.log(er);
        });
    }
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <ScrollView style={styles.container}>
      <View style={styles.wrap}>
        <Button title={'back'} onPress={back} />
        <Title title={'FIRST LAUNCH'} />
        {/*
first launch
        */}
        <Box title={'install referrer(1-5)'}>
          {generateItems(installReferrer)}
        </Box>
        {/*
reinstall
        */}
        <Box title={'reinstall(6)'}>
          {reinstall ? <List title={'launch_id'} value={reinstall} /> : null}
        </Box>
        <Title title={'EACH SESSION'} />
        {/*
app version
        */}
        <Box title={'app version(7)'}>
          <List title={'app_version'} value={Toolkit.getAppVersion() ?? ''} />
        </Box>
        {/*
session
        */}
        <Box title={'session(8)'}>
          <List
            title={'session_id'}
            value={Toolkit.getSessionId() ?? Toolkit.createSessionId()}
          ></List>
        </Box>
        {/*
location
        */}
        <Box title={'location(9)'}>
          {location ? generateItems(location) : null}
        </Box>
        {/*
local data & time
        */}
        <Box title={'lcoal date & time(10)'}>
          <List title={'date_time'} value={Toolkit.getLocalDateTime()} />
        </Box>

        {/*
os parameters
        */}
        <Box title={'os parameters(16)'}>{generateItems(OSParameters)}</Box>
        <Title title={'Binding to an event'} />
        {/*
fingerprint
        */}
        <Box title={'fingerprint(17)'}>{generateItems(fingerprint)}</Box>
        {/*
sms
        */}
        <Box title={'SMS(18)'}>
          {sms.map((sms) => (
            <List
              key={sms.id}
              title={sms.sender}
              value={`${new Date(sms.date).toLocaleDateString()}\n${sms.message.substring(0, 40)}...`}
            />
          ))}
          <View style={{ alignSelf: 'center' }}>
            <List title={'length'} value={sms.length} />
          </View>
        </Box>
        {/*
contacts
        */}
        <Box title={'контакты(19)'}>
          {contacts.map((contact) => (
            <List key={contact.id} title={contact.name} value={contact.phone} />
          ))}
          <View style={styles.center}>
            <List title={'length'} value={contacts.length} />
          </View>
        </Box>
        {/*
callLogs
        */}
        <Box title={'список вызовов за 7 дней(20)'}>
          {calls.length
            ? calls.map((call) => (
                <List
                  key={call.date}
                  title={call.name ? call.name : call.number}
                  value={`${new Date(call.date).toLocaleDateString()} ${new Date(call.date).getHours()}:${new Date(call.date).getMinutes()}, ${call.duration} sec`}
                />
              ))
            : null}
          <View style={styles.center}>
            <List title={'length'} value={calls.length} />
          </View>
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
  center: {
    alignSelf: 'center',
  },
});

export default AllData;
