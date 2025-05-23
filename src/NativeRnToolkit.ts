import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { ICall, IContact, ISMS } from './interfaces';

export interface ICallFilters {
  minDate?: number;
  limit?: number;
}

export interface Spec extends TurboModule {
  /**
   * On the first launch after (re)installing the app
   * */
  getIsFirstLaunch(): boolean;
  getInstallReferrer(): Promise<Record<string, any> | null>;
  getInstallId(): string;
  /**
   * Each session
   * */
  createSessionId(): string; // вызывается когда создается сессия
  getSessionId(): string | null; // может потребоваться гдето внутри сессии
  getLocalDateTime(): string;
  getOSParameters(): Promise<Record<string, any>>;
  getAppVersion(): string | null;
  getCoarseLocation(): Promise<Record<string, any>>;

  /**
   * Binding to an event
   * */
  getCalls(filters?: ICallFilters): Promise<ICall[]>;
  getContacts(filters?: { limit?: number }): Promise<IContact[]>;
  getSMS(filters?: { limit?: number }): Promise<ISMS[]>;
  getFingerprint(): Promise<Record<string, any>>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RnToolkit');
