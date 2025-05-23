import type { FC, PropsWithChildren } from 'react';
import { StyleSheet, Text, View } from 'react-native';

const Box: FC<{ title: string } & PropsWithChildren> = ({
  title,
  children,
}) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>{title}</Text>
      {children}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'gray',
    padding: 10,
    marginHorizontal: 10,
    borderRadius: 5,
  },
  title: {
    textAlign: 'center',
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
  },
});

export default Box;
