export interface IInstallReferrer {
  installReferrer: string; // URL-адрес реферера установленного пакета.
  referrerClickTimestampSeconds: number; // Временная метка в секундах, когда произошел клик реферера (как на стороне клиента, так и на стороне сервера).
  installBeginTimestampSeconds: number; // Временная метка в секундах начала установки (как на стороне клиента, так и на стороне сервера).
  installVersion: string | null; // Версия приложения на момент его первой установки.
  googlePlayInstantParam: boolean; // Взаимодействовал ли пользователь с мгновенным интерфейсом вашего приложения за последние 7 дней.
  launch_id: string;
}
