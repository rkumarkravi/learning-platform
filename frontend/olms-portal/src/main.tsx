import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import AuthLayout from "./pages/auth/AuthLayout.tsx";
import MainLayout from "./pages/main/MainLayout.tsx";
import { ToastProvider } from "./components/ui/toast.tsx";
import React from "react";
import { ThemeProvider } from "./components/ThemeProvider.tsx";
import store from "./store/LmsStore.tsx";
import { Provider } from 'react-redux';

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
  <React.StrictMode>
    <Provider store={store}>
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <ToastProvider>
          <RouterProvider router={router} />
        </ToastProvider>
      </ThemeProvider>
    </Provider>
  </React.StrictMode>
);
