import { StyleSheet, Text } from 'react-native';

const Title = ({ title }: { title: string }) => {
  return <Text style={styles.text}>{title}</Text>;
};

const styles = StyleSheet.create({
  text: {
    textAlign: 'center',
    color: 'white',
    fontSize: 20,
  },
});

export default Title;
