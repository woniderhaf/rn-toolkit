# rn-toolkit

## Description

| Название метода           | Тип возвращаемого значения                        | Описание                                                                         |
|---------------------------|------------------------------------------------|----------------------------------------------------------------------------------|
| `getIsFirstLaunch`        | `boolean`                                      | Проверяет, является ли текущий запуск приложения первым                          |
| `getInstallReferrer`      | `Promise<Record<string, any> \| null>`         | Возвращает реферер установки приложения                                          |
| `getInstallId`           | `string`                                       | Возвращает уникальный идентификатор установки                                    |
| `createSessionId`        | `string`                                       | Создает и возвращает новый идентификатор сессии (вызывается при создании сессии) |
| `getSessionId`           | `string \| null`                               | Возвращает текущий идентификатор сессии (может быть null)                        |
| `getLocalDateTime`       | `string`                                       | Возвращает локальное дату и время в строковом формате                            |
| `getOSParameters`        | `Promise<Record<string, any>>`                 | Возвращает параметры операционной системы                                        |
| `getAppVersion`          | `string \| null`                               | Возвращает версию приложения                                                     |
| `getCoarseLocation`      | `Promise<Record<string, any>>`                 | Возвращает приблизительные геоданные                                             |
| `getCalls`               | `Promise<ICall[]>`                             | Возвращает список звонков с возможностью фильтрации                              |
| `getContacts`            | `Promise<IContact[]>`                          | Возвращает список контактов с возможностью ограничения количества                |
| `getSMS`                 | `Promise<ISMS[]>`                              | Возвращает список SMS с возможностью ограничения количества                      |
| `getFingerprint`         | `Promise<Record<string, any>>`                 | Возвращает объект данных устройства для расчет фингерпртнта                      |

## Usage


```js
import Toolkit from 'rn-toolkit';

// ...
  const appVersion = Toolkit.getAppVersion()
```


Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
