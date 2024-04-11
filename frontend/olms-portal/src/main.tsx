import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import AuthLayout from "./pages/auth/AuthLayout.tsx";
import MainLayout from "./pages/main/MainLayout.tsx";
import { ToastProvider } from "./components/ui/toast.tsx";
import { createContext, useContext, useState } from "react";
import React from "react";

const DataContext = createContext({});

export const DataProvider = ({ children }) => {
  const [data, setData] = useState({}); // Initial data state

  return (
    <DataContext.Provider value={{ data, setData }}>
      {children}
    </DataContext.Provider>
  );
};
export const useData = () => useContext(DataContext);

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    // errorElement: <ErrorPage />,
    children: [
      {
        element: <AuthLayout />,
        index: true,
      },
      {
        element: <MainLayout />,
        path: "/main",
      },
      // {
      //   element: <CreateRecipeV3 />,
      //   path:"/create-your-recipe"
      // },{
      //   element: <ViewRecipe />,
      //   path:"/view-recipe/:recipeId",
      //   loader: async ({ params }) => {
      //     return fetch(`/recipes/${params.recipeId}`);
      //   },
      // },
    ],
  },
]);
ReactDOM.createRoot(document.getElementById("root")!).render(
  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>,
  <React.StrictMode>
    <DataProvider>
      <ToastProvider>
        <RouterProvider router={router} />
      </ToastProvider>
    </DataProvider>
  </React.StrictMode>
);
