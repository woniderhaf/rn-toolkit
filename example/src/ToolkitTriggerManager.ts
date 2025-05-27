import type {
  IToolkitParam,
  IToolkitTrigger,
  IToolkitTriggerManager,
  IToolkitTriggerResult,
} from './interfaces';

class ToolkitTriggerManagerImpl implements IToolkitTriggerManager {
  runTrigger(trigger: IToolkitTrigger) {
    return this.handler(trigger);
  }

  async sendDataInServer(data: IToolkitTriggerResult[]): Promise<void> {
    console.log(data);
  }

  private handler(
    id_IToolkitParams: IToolkitTrigger
  ): Promise<Array<IToolkitTriggerResult>> {
    const IToolkitParams =
      this.createNativeIToolkitParamsArray(id_IToolkitParams);

    return Promise.allSettled(this.funcCall(IToolkitParams)).then((res) => {
      return this.prepareIToolkitParams(res, id_IToolkitParams);
    });
  }

  private funcCall(IToolkitParams: Array<IToolkitParam>) {
    return IToolkitParams.map((f) => f());
  }

  private createNativeIToolkitParamsArray(
    IToolkitParameters: IToolkitTrigger
  ): Array<IToolkitParam> {
    return Object.keys(IToolkitParameters)
      .map((key) => {
        return IToolkitParameters[key];
      })
      .filter((item) => !!item);
  }

  private prepareIToolkitParams(
    result: Array<PromiseSettledResult<any>>,
    IToolkitParams: IToolkitTrigger
  ): Array<IToolkitTriggerResult> {
    const data: Array<IToolkitTriggerResult> = [];

    result.forEach((IToolkitParametr, index) => {
      const id = Object.keys(IToolkitParams)[index];

      if (!id) {
        return;
      }
      if (IToolkitParametr.status === 'fulfilled') {
        data.push({ id, value: IToolkitParametr.value });
      } else {
        console.log('error', id, IToolkitParametr.reason);
      }
    });

    data.filter((item) => !!item.value);

    return data;
  }
}

const ToolkitTriggerManager = new ToolkitTriggerManagerImpl();

export default ToolkitTriggerManager;
export type { IToolkitTriggerResult, IToolkitTrigger };
