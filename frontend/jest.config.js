/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: "ts-jest",
  // testEnvironment: "node",
  rootDir: ".",
  testMatch: ["**/?(*)+(spec).tsx"],
  resetMocks: true,
  clearMocks: true,
  roots: ["<rootDir>"],
  transform: {
    "^.+\\.(t|j)sx?$": "ts-jest",
  },
  moduleFileExtensions: ["ts", "js", "tsx", "json", "node"],
  collectCoverage: true,
  clearMocks: true,
  coverageDirectory: "coverage",
};
