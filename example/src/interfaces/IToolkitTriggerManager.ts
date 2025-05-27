import { type IToolkitTriggerResult } from './IToolkitTriggerResult';
import { type IToolkitTrigger } from './IToolkitTrigger';

export interface IToolkitTriggerManager {
  runTrigger(data: IToolkitTrigger): Promise<IToolkitTriggerResult[]>;
  sendDataInServer(data: IToolkitTriggerResult[]): Promise<void>;
}
