import { StyleSheet, Text, View } from 'react-native';
import { List } from '../components';

export const generateItems = (data: Record<string, any>) => {
  const keys = Object.keys(data);
  return (
    <>
      {keys.map((key) => {
        if (data.hasOwnProperty(key)) {
          if (data[key] !== null && typeof data[key] === 'object') {
            return (
              <View key={key} style={styles.container}>
                <Text style={styles.text}>{key} (object)</Text>
                {generateItems(data[key])}
              </View>
            );
          }
          return (
            <List
              key={JSON.stringify(data[key]) + key}
              title={key}
              value={JSON.stringify(data[key])}
            />
          );
        }
        return <></>;
      })}
      <List title={'length'} value={keys.length} />
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'rgba(0, 0, 0, 0.3)',
    borderRadius: 10,
    padding: 10,
    marginBottom: 5,
  },
  text: {
    textAlign: 'center',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
