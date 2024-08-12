import { myAxios } from "./helper";

export const loadAllCategories = () => {
  return myAxios.get(`/category/gets`).then((respone) => {
    return respone.data;
  });
};
