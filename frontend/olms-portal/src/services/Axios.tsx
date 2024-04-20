import { ToastAction } from "@/components/ui/toast";
import { toast } from "@/components/ui/use-toast";
import axios from "axios";

const apiBaseUrl = "http://localhost:8089"; // Replace with your API base URL
const authorizationBypass = ["/auth/login", "/auth/register"];
const axiosService = async (
  method: string,
  url: string,
  req: unknown = null,
  headers: any = {}
) => {
  try {
    if (
      !authorizationBypass.some((x) => url.indexOf(x) > -1) &&
      !headers["Authorization"] &&
      localStorage.getItem("at")
    ) {
      headers["Authorization"] = `Bearer ${localStorage.getItem("at")}`;
    }
    const response = await axios({
      method: method,
      url: `${apiBaseUrl}${url}`,
      data: req,
      headers: headers,
    });
    // setData({ ...data, loader: false });
    return response.data;
  } catch (error: any) {
    // Handle error
    // setData({ ...data, loader: false });

    console.error("Axios error:");
    throw error;
  }
};

export default axiosService;
