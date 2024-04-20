import {
  FileImage,
  FileText,
  FileVideo,
  GalleryHorizontalEnd,
  Trash2,
} from "lucide-react";
import React from "react";

function MediaViewer({
  contentId,
  type,
  showDeleteIcon,
  deleteAction,
  className,
}) {
  const url = `http://localhost:8089/media/${contentId}`;
  const getMedia = () => {
    switch (type) {
      case "img":
        return (
          <a href={url}>
            <FileImage className={className} />
          </a>
        );
      case "pdf":
        return (
          <a href={url}>
            <FileText className={className} />
          </a>
        );
      case "ppt":
        return (
          <a href={url}>
            <GalleryHorizontalEnd className={className} />
          </a>
        );
      case "video":
        return (
          <a href={url}>
            <FileVideo className={className} />
          </a>
        );
    }
  };
  return (
    <div className="relative">
      {showDeleteIcon && (
        <Trash2
          className="absolute right-0 top-0 bg-slate-500 p-1 rounded-full cursor-pointer"
          onClick={deleteAction}
        />
      )}
      {getMedia()}
    </div>
  );
}

export default MediaViewer;
