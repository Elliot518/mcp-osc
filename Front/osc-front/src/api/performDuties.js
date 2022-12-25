import request from "../utils/request";

export function fetchMeetingType() {
  return request({
    url: "/duty-meeting-type/list",
    method: "get",
  });
}
export function fetchDirectors() {
  return request({
    url: "/duty-dob/names",
    method: "get",
  });
}
export function fetchMatterType() {
  return request({
    url: "/duty-issue-type/list",
    method: "get",
  });
}
//获取随机id
export function fetchGenericId() {
  return request({
    url: "/generic-id/",
    method: "get",
  });
}
