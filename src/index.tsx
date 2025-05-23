import NativeRnToolkit from './NativeRnToolkit';

type Param = () => any | Promise<any>;
type IdParamsType = Record<string, Param>;
type IResult = { id: string; value: any };

class NativeModulesTriggersImpl {
  firstLaunchTrigger(id_params: IdParamsType) {
    return this.handler(id_params);
  }

  newSessionTrigger(id_params: IdParamsType) {
    return this.handler(id_params);
  }

  private handler(id_params: IdParamsType): Promise<Array<IResult>> {
    const params = this.createNativeParamsArray(id_params);

    console.log('handler', params);
    return Promise.allSettled(this.funcCall(params)).then((res) => {
      return this.prepareParams(res, id_params);
    });
  }

  private funcCall(params: Array<Param>) {
    console.log('funcCall', params);
    return params.map((f) => f());
  }

  private createNativeParamsArray(parameters: IdParamsType): Array<Param> {
    const params = Object.keys(parameters)
      .map((key) => {
        return parameters[key];
      })
      .filter((item) => !!item);

    return params;
  }

  private prepareParams(
    result: Array<PromiseSettledResult<any>>,
    params: IdParamsType
  ): Array<IResult> {
    const data: Array<IResult> = [];
    console.log(result);
    result.forEach((parametr, index) => {
      const id = Object.keys(params)[index];
      if (!id) {
        return;
      }
      if (parametr.status === 'fulfilled') {
        data.push({ id, value: parametr.value });
      } else {
        console.log('error', id, parametr.reason);
      }
    });

    data.filter((item) => !!item.value);

    return data;
  }
}

const NativeModulesTriggers = new NativeModulesTriggersImpl();

export default NativeRnToolkit;
export { NativeModulesTriggers };
export type { IdParamsType, Param, IResult };
