import { StyleSheet, Text } from 'react-native';

const List = ({ title, value }: { title: string; value: string | number }) => {
  return (
    <Text style={styles.text}>
      {title}: <Text style={styles.value}>{value}</Text>
    </Text>
  );
};

const styles = StyleSheet.create({
  text: {
    fontWeight: 'bold',
    fontSize: 17,
  },
  value: {
    fontWeight: 'normal',
    color: 'white',
    fontSize: 15,
  },
});

export default List;
