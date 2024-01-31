/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: "ts-jest",
  rootDir: ".",
  testMatch: ["**/?(*)+(spec).tsx"],
  resetMocks: true,
  clearMocks: true,
  roots: ["<rootDir>"],
  transform: {
    "^.+\\.(t|j)sx?$": "ts-jest",
  },
  moduleFileExtensions: ["ts", "js", "tsx", "json", "node", "css"],
  collectCoverage: true,
  clearMocks: true,
  coverageDirectory: "coverage",
  testEnvironment: "jsdom",
  testEnvironmentOptions: {
    customExportConditions: [""],
  },

  //css unexpected token
  moduleNameMapper: {
    "\\.(css|less|scss|sass)$": "identity-obj-proxy",
  },
};
