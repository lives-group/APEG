// {{ dg-checkwhat "c-analyzer" }}

char foo (char *x)
{
  return *x;
}

void bar (char *x)
{
  void *arr[foo (x)] __attribute__((unused));
}

void baz (char *x)
{
  bar (x);
}
