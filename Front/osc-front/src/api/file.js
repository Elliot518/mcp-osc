import request from "../utils/request";
export function updateFile(data) {
  return request({
    url: "/upload/batch",
    method: "post",
    data,
  });
}

export function fetchWordCloud(query) {
  return request({
    url: "/word-cloud/" + query,
    method: "get",
  });
}
export function fetchFileList(query) {
  return request({
    url: "/queue/batches",
    method: "get",
    params: query,
  });
}
export function fetchFileListView(query) {
  return request({
    url: "/queue/batches/" + query.batchId + "/files",
    method: "get",
  });
}
//任务中心接口
export function fetchTaskCenter(query) {
  return request({
    url: "/schedule/jobs",
    method: "get",
    params: query,
  });
}
export function fetchTaskViewList(query) {
  return request({
    url: "/task/taskViewList",
    method: "get",
    params: query,
  });
}
//停用词api
export function importStopWord(data) {
  return request({
    url: "/upload/batch",
    method: "post",
    data,
  });
}
export function fetchStopWordFileList(query) {
  return request({
    url: "/stop-words/list",
    method: "get",
    params: query,
  });
}
//停用词启用接口调用
export function openStopWord(id) {
  return request({
    url: "/stop-words/updateStopWordsStatus/" + id,
    method: "post",
  });
}
